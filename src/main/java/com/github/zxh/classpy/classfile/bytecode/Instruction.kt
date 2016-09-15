package com.github.zxh.classpy.classfile.bytecode;

import com.github.zxh.classpy.classfile.ClassComponent;
import com.github.zxh.classpy.classfile.constant.ConstantPool;
import com.github.zxh.classpy.classfile.reader.ClassReader;

import java.util.List;

/**
 * Base class for all instructions.
 */
public class Instruction extends ClassComponent {

    protected final Opcode opcode;
    protected final int pc;

    public Instruction(Opcode opcode, int pc) {
        this.opcode = opcode;
        this.pc = pc;
        setDesc(opcode.name());
    }

    public int getPc() {
        return pc;
    }
    
    @Override
    protected final void readContent(ClassReader reader) {
        if (!super.getSubComponents().isEmpty()) {
            super.readContent(reader);
        } else {
            reader.readUnsignedByte(); // opcode
            readOperands(reader);
        }
    }
    
    protected void readOperands(ClassReader reader) {
        if (opcode.operandCount > 0) {
            reader.skipBytes(opcode.operandCount);
        }
    }

    @Override
    protected void afterRead(ConstantPool cp) {
        List<ClassComponent> subComponents = super.getSubComponents();
        if (subComponents.size() == 2) {
            ClassComponent operand = subComponents.get(1);
            setDesc(opcode.name() + operand.getDesc());
        } else {
            // todo
        }
    }

}